NAME

ADMISSION NO. - J31S/14437/2019


SUPERVISOR- DR. MICHAEL MUNYAO

TITLE: UNICITE INTEGRATED LEARNING SYSTEM FOR ENHANCED BLENDED LEARNING



Installation of the system
To install the Unicite Integrated Learning System (ILS) on your local machine, follow the steps below:

1. Prerequisites:
   - Ensure you have Java Development Kit (JDK) installed on your system.
   - Install a compatible Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse.
   - Make sure you have a MySQL or PostgreSQL database server installed and running.

2. Clone the Project:
   - Open your IDE and clone the Unicite ILS project from the Git repository: 
		
		https://github.com/kimkung43-oss/Unicite

   - Alternatively, you can download the project source code as a ZIP file and extract it to a local directory.

3. Configure the Database:
   - Open the project in your IDE and navigate to the configuration files, usually located in the `src/main/resources` directory.
   - Update the database connection settings in the `application.properties` file to match your local database configuration.
   - Provide the database name, username, and password.

4. Build and Run the Project:
   - Use your IDE's build tools to compile and package the project.
   - Once the build is successful, run the application by executing the main class, typically named `Application.java`.
   - The application will start and deploy on your local server (e.g., Tomcat) at `http://localhost:8080`.

5. Access the Unicite ILS:
   - Open your web browser and navigate to `http://localhost:8080` to access the Unicite ILS.
   - You should see the landing page or login screen, depending on the authentication settings.
   - Use the provided credentials to log in as an administrator, teacher, or student, depending on your role.

6. Explore and Test:
   - Once logged in, explore the various features and functionalities of Unicite ILS.
   - Create courses, add materials, schedule classes, and interact with other users to test the system's capabilities.
   - Make sure to test different scenarios, such as submitting assignments, participating in discussions, and accessing resources.

Congratulations! You have successfully installed the Unicite Integrated Learning System (ILS) on your local machine. You can now start using and customizing the system according to your needs and preferences.