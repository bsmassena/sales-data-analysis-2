# Sales data analysis

## Running this project

1. Create the directories $HOME/data/in and $HOME/data/out

2. Move the files you want to be processed into the $HOME/data/in directory (this can also be done after running the application). These files must be in the following format, where the first column identify the data type and `ç` is used as the column separator

    ```
    001ç1234567891234çPedroç50000
    001ç3245678865434çPauloç40000.99
    002ç2345675434544345çJose da SilvaçRural
    002ç2345675433444345çEduardo PereiraçRural
    003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
    003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
    ```

   * 001: salesman data with the format
  
        *001çCPFçNameçSalary*

    * 002: customer data with the format
  
        *002çCNPJçNameçBusiness Area*

    * 003: sale data with the format
  
        *003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name*

3. Run this application with the command

    ```
    ./gradlew run
    ```

4. After that, the output files should be inside $HOME/data/out directory, with the following data:

    * Amount of customers on the input file
    * Amount of salesman on the input file
    * ID of the most expensive sale
    * Name of the worst salesman
