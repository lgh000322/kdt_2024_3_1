import React, { useState } from "react";
import styled from "styled-components";
import HeaderComponent from "../components/HeaderComponent";

const PageWrapper = styled.div`
  min-height: 100vh;
  background-color: #f5f7fa;
`;

const Container = styled.div`
  max-width: 600px;
  margin: 180px auto;
  padding: 40px;
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
`;

const Title = styled.h1`
  text-align: center;
  font-size: 28px;
  color: #2d3748;
  margin-bottom: 40px;
  font-weight: 700;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 24px;
`;

const FormGroup = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

const Label = styled.label`
  min-width: 90px;
  font-size: 15px;
  color: #4a5568;
  font-weight: 600;
`;

const Input = styled.input`
  flex: 1;
  padding: 12px 16px;
  border-radius: 8px;
  border: 2px solid #e2e8f0;
  font-size: 15px;
  background-color: white;
  transition: all 0.2s ease;

  &:focus {
    border-color: #6c63ff;
    box-shadow: 0 0 0 3px rgba(108, 99, 255, 0.1);
    outline: none;
  }

  &::placeholder {
    color: #a0aec0;
  }
`;

const RadioGroup = styled.div`
  display: flex;
  gap: 24px;
  flex: 1;
`;

const RadioLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-size: 15px;
  color: #4a5568;

  input[type="radio"] {
    width: 18px;
    height: 18px;
    accent-color: #6c63ff;
  }
`;

const DisplayField = styled.p`
  flex: 1;
  font-size: 15px;
  color: #4a5568;
  padding: 12px 16px;
  background-color: #f8f9fa;
  border-radius: 8px;
  margin: 0;
`;

const Button = styled.button`
  width: 100%;
  padding: 14px;
  background-color: #6c63ff;
  color: white;
  font-size: 16px;
  font-weight: 600;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  margin-top: 16px;

  &:hover {
    background-color: #5a52d5;
    transform: translateY(-1px);
  }

  &:active {
    transform: translateY(0);
  }
`;

function LoginSuccessPage() {
    const cookies = document.cookie.split(';');

    cookies.forEach((cookie) => {
  const [name, value] = cookie.trim().split('=');
  console.log(`${name}=${value}`);
});
    const [formData, setFormData] = useState({
        email: "",
        name: "",
        gender: "남자",
        phone: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form Submitted:", formData);
    };

    return (
        <PageWrapper>
            <HeaderComponent role="consumer" />
            <Container>
                <Title>회원 정보 입력</Title>
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label>이메일</Label>
                        <DisplayField>{formData.email}</DisplayField>
                    </FormGroup>

                    <FormGroup>
                        <Label>이름</Label>
                        <DisplayField>{formData.name}</DisplayField>
                    </FormGroup>

                    <FormGroup>
                        <Label>성별</Label>
                        <RadioGroup>
                            <RadioLabel>
                                <input
                                    type="radio"
                                    name="gender"
                                    value="남자"
                                    checked={formData.gender === "남자"}
                                    onChange={handleChange}
                                />
                                남자
                            </RadioLabel>
                            <RadioLabel>
                                <input
                                    type="radio"
                                    name="gender"
                                    value="여자"
                                    checked={formData.gender === "여자"}
                                    onChange={handleChange}
                                />
                                여자
                            </RadioLabel>
                        </RadioGroup>
                    </FormGroup>

                    <FormGroup>
                        <Label htmlFor="phone">전화번호</Label>
                        <Input
                            id="phone"
                            type="tel"
                            name="phone"
                            value={formData.phone}
                            onChange={handleChange}
                            placeholder="전화번호를 입력해주세요"
                            required
                            pattern="[0-9]*"
                            maxLength={11}
                        />
                    </FormGroup>

                    <Button type="submit">회원가입</Button>
                </Form>
            </Container>
        </PageWrapper>
    );
}

export default LoginSuccessPage;
