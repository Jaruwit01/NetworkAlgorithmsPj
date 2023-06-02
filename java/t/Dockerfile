# เลือกเวอร์ชันของ Java Runtime Environment (JRE) ที่คุณต้องการใช้
FROM openjdk:11

# กำหนดไดเร็กทอรี่ที่จะเก็บแอปพลิเคชัน Java
WORKDIR /app

# คัดลอกไฟล์ Java ไปยังไดเร็กทอรี่ /app ใน Docker ระบบ
COPY TSPServer.java /app/

# คอมไพล์โปรแกรม Java
RUN javac TSPServer.java

# เมื่อสร้าง Docker container จะเริ่มโปรแกรม Java โดยอัตโนมัติ
CMD ["java", "TSPServer"]
