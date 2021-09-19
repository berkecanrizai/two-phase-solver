
public class QuestionUI {
	int variableNum;
	int constraintNum;
	
	public QuestionUI(int variableNum, int constraintNum) {
		this.variableNum=variableNum;
		this.constraintNum=constraintNum;
		try {
			Window window = new Window();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
