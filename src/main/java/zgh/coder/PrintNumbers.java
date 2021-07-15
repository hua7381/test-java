package zgh.coder;

public class PrintNumbers {

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= 56; i++) {
			sb.append("," + i);
		}
		System.out.println(sb.toString());
	}

}
