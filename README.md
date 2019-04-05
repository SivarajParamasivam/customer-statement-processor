# Customer Statement Processor
This is a standalone Java project to validate and process the Customer records.
Validations is based on the transaction references ID and end balance, At the end of processing Failed records are placed in the given path in CSV format.
Which will display both the transaction reference and description of each of the failed records.

# Getting Started

  Clone this project from  git - https://github.com/SivarajParamasivam/customer-statement-processor.git
  Project Clone:
  git clone https://github.com/SivarajParamasivam/customer-statement-processor.git

  Then import the project in eclipse and run as java application after placing the required files in the given path and specify the path in the readFilepath and writeFilePath attributes.

# Prerequisites

  JDK/JRE greater than 5 should be available to execute this strand alone project.
  The File which needs to be processed has to place  in a accessible path and that path has to be given as a input.
  Which location the file needs to be read and in which location output file should be generated.
  Examples:

  readFilePath = "C:\\customer-statements";
  writeFilePath = "C:\\customer-statements\\error-report\\";

# Built With
  •	Core Java
# Test
   The Junit Validations is based on the transaction references ID and end balance 
   
# Author
  Sivaraj Paramasivam- complete project  


