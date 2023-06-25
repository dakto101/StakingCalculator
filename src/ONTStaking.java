
public class ONTStaking {

	// Input 
	
	
	static double stakedONT = 4090;
	// Average consensus round time in days
	static double averageConsensusRoundTime = 12;
	// Number of round per claim
	static int roundPerClaim = 4; 
	// APR
	static double annualPercentageRate = 0.247;
	
	// Price in USD
	static double ontPrice = 0.1939;
	// Price in USD
	static double ongPrice = 0.2279;
	static double ontOngRatio = ontPrice/ongPrice;
	
	// Fee from Binance Withdrawal ONT
	static double feeONT = 1;
	// Fee from Ontology transaction fee
	static double feeTransactionONG = 0.05;
	static double feeTransactionInONT = feeTransactionONG * ongPrice / ontPrice;
	static double totalFeePerClaim = feeONT + feeTransactionInONT;
	
	public static void main(String[] args) {
		for (roundPerClaim = 1; roundPerClaim <= 15; roundPerClaim++) {
			System.out.println("Round per claim = " + roundPerClaim + ", final ONT = " + calculate(false, false));
		}
		roundPerClaim = 3;
		//calculate(true, true);
	}
	
	private static double calculate(boolean showDetails, boolean showResult) {
		int round = 0;
		double claimableONT = 0;
		// ONT will add to account instantly and take more round to re-stake.
		double processingONT = 0;
		double tempStakedONT = stakedONT;
		for (double i = 0; i <= 365; i = i + averageConsensusRoundTime, round++) {
			
			// Calc
			double earnedONTPerRound = tempStakedONT * annualPercentageRate / 365 * averageConsensusRoundTime;
			boolean claim = (round > 0 && (round % roundPerClaim == 0)) ? true : false;
			
			if (processingONT > 0) {
				tempStakedONT += processingONT;
				processingONT = 0;
			}

			
			claimableONT += earnedONTPerRound;
			if (round == 0) claimableONT = 0;

			if (claim) {
				processingONT = claimableONT - totalFeePerClaim;
			}
			
			// Output
			if (showDetails) {
				System.out.println("");
				System.out.println("---------Day " + i + ", Round " + round + "-----------");
				
				System.out.println("Is claim: \t\t"+ claim);
				System.out.println("Processing: \t\t" + processingONT);
				System.out.println("Claimable ONT: \t\t" + claimableONT);
				System.out.println("Staked ONT (Final): \t" + tempStakedONT);
				System.out.println("Total ONT: \t\t" + (tempStakedONT + processingONT));
				
				System.out.println("---------Day " + i + ", Round " + round + "-----------");
				System.out.println("");
				
			}
			//
			// Calc
			if (claim) {
				claimableONT = 0;
			}
		}
		
		if (showResult) System.out.println("Final ONT: \t" + (tempStakedONT + processingONT + claimableONT));
		return tempStakedONT + processingONT + claimableONT;

	}

}
