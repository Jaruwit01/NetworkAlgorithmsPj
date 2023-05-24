FROM python:3.11-slim

# Install required dependencies
RUN apt-get update && \
    apt-get install -y iproute2 net-tools

# Copy the Python script to the container
COPY tsp_solver.py /app/tsp_solver.py

# Set the working directory
WORKDIR /app

# Run the Python script
CMD ["python", "tsp_solver.py"]
