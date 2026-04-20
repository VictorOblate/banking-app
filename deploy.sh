#!/bin/bash

# Online Banking Application - Deployment Script
# This script sets up and deploys the banking application on a local machine
# Run this script on your laptop after transferring the project files

set -e

echo "🚀 Online Banking Application Deployment Script"
echo "=============================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check prerequisites
check_prerequisites() {
    print_status "Checking prerequisites..."

    # Check Java
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed. Please install JDK 8 or higher."
        exit 1
    fi

    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 8 ]; then
        print_error "Java 8 or higher is required. Current version: $JAVA_VERSION"
        exit 1
    fi
    print_status "Java version: $(java -version 2>&1 | head -n 1)"

    # Check if we're in the project directory
    if [ ! -f "build.xml" ]; then
        print_error "Please run this script from the banking-app project root directory."
        exit 1
    fi

    print_status "Prerequisites check completed."
}

# Setup MySQL database
setup_database() {
    print_status "Setting up MySQL database..."

    # Check MySQL
    if ! command -v mysql &> /dev/null; then
        print_error "MySQL is not installed. Please install MySQL Server 5.5 or higher."
        print_status "On Ubuntu/Debian: sudo apt-get install mysql-server"
        print_status "On macOS: brew install mysql"
        exit 1
    fi

    print_status "MySQL version: $(mysql --version)"

    # Create database and import schema
    print_status "Creating database and importing schema..."
    mysql -u root -p << EOF
CREATE DATABASE IF NOT EXISTS banking_db DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON banking_db.* TO 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
EOF

    # Import schema
    mysql -u root -p banking_db < database/schema.sql

    print_status "Database setup completed."
}

# Setup Payara/GlassFish server
setup_application_server() {
    print_status "Setting up application server..."

    # Check if Payara/GlassFish is available
    if [ ! -d "payara5" ] && [ ! -d "glassfish4" ]; then
        print_warning "Payara/GlassFish not found in project directory."
        print_status "Please download and extract one of the following:"
        echo "  - Payara Server 5.x: https://repo1.maven.org/maven2/fish/payara/distributions/payara/"
        echo "  - GlassFish 4.0: https://repo1.maven.org/maven2/org/glassfish/main/distributions/glassfish/4.0/"
        echo ""
        print_status "Extract to 'payara5' or 'glassfish4' directory in the project root."

        read -p "Press Enter after setting up the application server..."
    fi

    # Determine server type
    if [ -d "payara5" ]; then
        SERVER_HOME="payara5"
        SERVER_NAME="Payara"
    elif [ -d "glassfish4" ]; then
        SERVER_HOME="glassfish4"
        SERVER_NAME="GlassFish"
    else
        print_error "No application server found."
        exit 1
    fi

    print_status "Using $SERVER_NAME server at $SERVER_HOME"

    # Build the application
    print_status "Building the application..."
    ant clean build

    # Start the server
    print_status "Starting $SERVER_NAME server..."
    export ${SERVER_NAME}_HOME="$(pwd)/$SERVER_HOME"
    cd "$SERVER_HOME/bin"
    ./asadmin start-domain domain1

    # Wait for server to start
    print_status "Waiting for server to start..."
    sleep 10

    # Deploy the application
    print_status "Deploying OnlineBanking application..."
    ./asadmin deploy "$(pwd)/../../dist/OnlineBanking.war"

    cd ../..

    print_status "Application server setup completed."
}

# Test the application
test_application() {
    print_status "Testing application deployment..."

    # Check if server is running
    if curl -s http://localhost:8080 > /dev/null; then
        print_status "Application server is running."

        # Check application deployment
        if curl -s -I http://localhost:8080/OnlineBanking | grep -q "HTTP/1.1 200"; then
            print_status "✅ Application deployed successfully!"
            print_status "🌐 Access the application at: http://localhost:8080/OnlineBanking"
            print_status "👤 Login credentials:"
            echo "   Admin: admin / admin123"
            echo "   Sample customers: ACC001001, ACC001002, etc."
        else
            print_warning "Application may not be fully deployed. Check server logs."
        fi
    else
        print_error "Application server is not responding."
    fi
}

# Main deployment process
main() {
    echo ""
    print_status "Starting Online Banking Application deployment..."
    echo ""

    check_prerequisites
    echo ""

    setup_database
    echo ""

    setup_application_server
    echo ""

    test_application
    echo ""

    print_status "🎉 Deployment completed!"
    print_status "📖 See SETUP_INSTRUCTIONS.md for detailed usage information."
}

# Cleanup function
cleanup() {
    print_status "Cleaning up..."
    # Stop server if running
    if [ -d "payara5" ]; then
        cd payara5/bin
        ./asadmin stop-domain domain1 2>/dev/null || true
        cd ../..
    elif [ -d "glassfish4" ]; then
        cd glassfish4/bin
        ./asadmin stop-domain domain1 2>/dev/null || true
        cd ../..
    fi
}

# Handle script interruption
trap cleanup SIGINT SIGTERM

# Run main function
main