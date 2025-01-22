package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";  //Reading Excel file from testData
		ExcelUtility e=new ExcelUtility(path);
		int totalrows=e.getRowCount("Sheet1");
		int totalcols=e.getCellCount("Sheet1", 1);
		String logindata[][]=new String[totalrows][totalcols];
		for(int i=1;i<=totalrows;i++)     /*This loop is to read data from Excel file and copy it to two dimessional array
			and why i=1 means , because i=0 is header part, we need to exclude it*/
		{
			for(int j=0;j<totalcols;j++)
			{
				logindata[i-1][j]=e.getCellData("Sheet1", i, j);  //why i-1, because array index starts from Zero
			}
		}
	return logindata; //returning two dimenssional array
	}
	
}
