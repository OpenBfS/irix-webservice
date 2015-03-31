/**
 * 
 */
package de.bfs.IRIXdocex;

import java.util.ArrayList;
import java.util.List;

import org.iaea._2012.irix.format.ReportType;
import org.iaea._2012.irix.format.identification.IdentificationType;
import org.iaea._2012.irix.format.identification.ReportingBasesType;

/**
 * @author Peter Bieringer
 * @version 0.1
 * 
 * IRIXbroker has to decide what to do with the information from an IRIX report
 * e.g. information for ELAN or measurements for VDB
 *
 */
public class IRIXbroker {
	
	public IRIXbroker(ReportType report)
	{
		if(report == null)
			System.exit(-1);
		else
		{
			IdentificationType ident= report.getIdentification();
			ReportingBasesType base= ident.getReportingBases();
			List<String> bases;
			bases = base.getReportingBasis();
			
			if(bases.isEmpty())
			{
				System.out.println("No reporting bases!");
				System.exit(-2);
			}
			for(int i=0;i<bases.size();i++)
			{
				String b=bases.get(i);
				
				//documents for ELAN
				if(b.equals("ESD"))
				{
					IRIXElanClient iec = new IRIXElanClient(report);
				}
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
