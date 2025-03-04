package shop.shopBE.global.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import shop.shopBE.domain.orderhistory.entity.OrderHistory;
import shop.shopBE.domain.orderhistory.repository.OrderHistoryRepository;
import shop.shopBE.domain.orderproduct.entity.OrderProduct;
import shop.shopBE.domain.orderproduct.entity.enums.DeliveryStatus;
import shop.shopBE.domain.orderproduct.repository.OrderProductRepository;

import java.util.Map;

@Configuration
public class OrderHistoryBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final OrderProductRepository orderProductRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryBatch(JobRepository jobRepository,
                             @Qualifier(value = "metaTransactionManager") PlatformTransactionManager transactionManager,
                             OrderProductRepository orderProductRepository,
                             OrderHistoryRepository orderHistoryRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.orderProductRepository = orderProductRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Bean
    public Job deleteOrderProduct() {
        return new JobBuilder("deleteOrderProduct",jobRepository)
                .start(deleteOrderProductStep())
                .next(deleteOrderProductStep())
                .build();
    }

    // 주문 상품을 제거하는 스텝
    @Bean
    public Step deleteOrderProductStep() {
        return new StepBuilder("deleteOrderProductStep", jobRepository)
                .<OrderProduct, OrderProduct>chunk(10, transactionManager)
                .reader(readOrderProduct())
                .processor(processOrderProduct())
                .writer(writeOrderProduct())
                .build();
    }

    @Bean
    public RepositoryItemReader<OrderProduct> readOrderProduct() {
        return new RepositoryItemReaderBuilder<OrderProduct>()
                .name("readOrderProduct")
                .pageSize(10)
                .methodName("findByCurrentDeliveryStatus")
                .arguments(DeliveryStatus.BEFORE_PAY)
                .repository(orderProductRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();

    }

    @Bean
    public ItemProcessor<OrderProduct,OrderProduct> processOrderProduct() {
        return new ItemProcessor<OrderProduct, OrderProduct>() {
            @Override
            public OrderProduct process(OrderProduct item) throws Exception {
                OrderHistory orderHistory = item.getOrderHistory();
                if (orderHistory != null && !orderHistory.isDeleted()) {
                    orderHistory.delete(); // delete 상태 변경
                    orderHistoryRepository.saveAndFlush(orderHistory);
                }

                return item;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<OrderProduct> writeOrderProduct() {
        return new RepositoryItemWriterBuilder<OrderProduct>()
                .repository(orderProductRepository)
                .methodName("delete")
                .build();
    }

}
