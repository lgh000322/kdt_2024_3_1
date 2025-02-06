import React, { useState } from 'react';
import AdminPageLayout from '../layouts/AdminPageLayout';

const userRole = "manager";  // 'seller', 'consumer', 'manager' 등 사용자가 가진 역할

// 더미 데이터 추가
const initialData = [
  { date: '2025-01-20', soldItems: 450, sales: '₩1,500,000' },
  { date: '2025-01-19', soldItems: 120, sales: '₩1,200,000' },
  { date: '2025-01-18', soldItems: 180, sales: '₩800,000' },
  { date: '2025-01-17', soldItems: 300, sales: '₩1,000,000' },
  { date: '2025-01-16', soldItems: 800, sales: '₩2,000,000' },
  { date: '2025-01-15', soldItems: 600, sales: '₩1,800,000' },
  { date: '2025-01-14', soldItems: 250, sales: '₩900,000' },
  { date: '2025-01-13', soldItems: 400, sales: '₩1,300,000' },
  { date: '2025-01-12', soldItems: 550, sales: '₩1,600,000' },
  { date: '2025-01-11', soldItems: 700, sales: '₩1,900,000' },
  { date: '2025-01-10', soldItems: 350, sales: '₩1,100,000' },
  { date: '2025-01-09', soldItems: 200, sales: '₩700,000' },
  { date: '2025-01-08', soldItems: 500, sales: '₩1,400,000' },
  { date: '2025-01-07', soldItems: 650, sales: '₩1,700,000' },
  { date: '2025-01-06', soldItems: 150, sales: '₩600,000' },
];

const Graph = ({ value, maxValue }) => {
  const percentage = (value / 3000) * 100; // 3000을 최대값으로 설정
  const ticks = [0, 500, 1000, 1500, 2000, 2500, 3000];

  return (
    <div style={{ position: 'relative', width: '100%', height: '20px', marginBottom: '15px' }}>
      <div style={{ 
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        height: '100%',
        backgroundColor: '#f0f0f0',
        borderRadius: '3px'
      }}>
        <div style={{ 
          width: `${percentage}%`, 
          height: '100%', 
          backgroundColor: '#007BFF', 
          borderRadius: '3px' 
        }}></div>
      </div>
      {ticks.map((tick) => (
        <div key={tick} style={{
          position: 'absolute',
          left: `${(tick / 3000) * 100}%`,
          top: '100%',
          transform: 'translateX(-50%)',
          fontSize: '10px',
          color: '#666'
        }}>
          {tick}
        </div>
      ))}
    </div>
  );
};

function AdminStatisticPage() {
  const [selectedDate, setSelectedDate] = useState('');
  const [filteredData, setFilteredData] = useState(initialData);

  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };

  const handleSearch = () => {
    if (selectedDate) {
      const filtered = initialData.filter(item => item.date === selectedDate);
      setFilteredData(filtered);
    } else {
      setFilteredData(initialData);
    }
  };

  return (
    <AdminPageLayout role={userRole}>
      {/* 검색 섹션 */}
      <div
        style={{
          padding: '80px',
          border: '1px solid #ddd',
          margin: '60px auto',
          width: '80%',
          backgroundColor: '#f7faff',
          borderRadius: '10px',
          boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
        }}
      >
        <h2
          style={{
            marginBottom: '10px',
            color: '#333',
            fontSize: '18px',
            fontWeight: 'bold',
          }}
        >
          통계 검색
        </h2>
        <div style={{ leftmargin: '10', alignItems: 'center' }}>
          <input
            type="date"
            style={{
              padding: '10px',
              border: '1px solid #ccc',
              borderRadius: '5px',
              marginRight: '10px',
              flexGrow: 1,
            }}
            value={selectedDate}
            onChange={handleDateChange}
          />
          <button
            style={{
              padding: '10px 20px',
              backgroundColor: '#007BFF',
              color: '#fff',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer',
              fontSize: '14px',
            }}
            onClick={handleSearch}
          >
            검색
          </button>
        </div>
      </div>

      {/* 테이블 섹션 */}
      <div
        style={{
          padding: '20px',
          border: '1px solid #ddd',
          margin: '60px auto',
          width: '80%',
          backgroundColor: '#fff',
          borderRadius: '10px',
          boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
        }}
      >  
        <h2 
          style={{
            marginBottom: "20px",
            color: "#333",
            fontSize: "20px",
            fontWeight: "bold"
          }}
        >
          통계 목록
        </h2>
        <div style={{ maxHeight: '400px', overflowY: 'auto' }}>
          <table style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead style={{ position: 'sticky', top: 0, backgroundColor: '#f7faff', zIndex: 1 }}>
              <tr>
                {['날짜', '그래프', '판매상품수', '판매액'].map((header) => (
                  <th
                    key={header}
                    style={{
                      borderBottom: '2px solid #ddd',
                      padding: '10px',
                      fontWeight: 'bold',
                      color: '#555',
                    }}
                  >
                    {header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {filteredData.map((row, index) => (
                <tr key={index} style={{ borderBottom: '1px solid #ddd' }}>
                  <td style={{ padding: '10px', textAlign: 'center', width: '15%' }}>{row.date}</td>
                  <td style={{ padding: '10px', textAlign: 'left', width: '50%' }}>
                    <Graph value={row.soldItems} maxValue={3000} />
                  </td>
                  <td style={{ padding: '10px', textAlign: 'center', width: '15%' }}>{row.soldItems}</td>
                  <td style={{ padding: '10px', textAlign: 'center', width: '20%' }}>{row.sales}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </AdminPageLayout>
  );
}

export default AdminStatisticPage;
