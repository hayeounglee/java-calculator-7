package calculator.domain;

public class InputStringSeparator {
    private String inputString;
    private String finalSeparator;
    private String customSeparator = null;
    private String[] inputNumber; // int vs integear ??

    public InputStringSeparator(String inputString) {
        setInputString(inputString);
        setCustomSeparator(getInputString());
        setFinalSeparator();
        // setInputNumber(inputString, this.finalSeparator);
    }

    private void validateInputString(String inputString) {

    }

    private String getInputString() {
        return this.inputString;
    }

    private void setInputString(String inputString) {
        inputString.replaceAll("\n", "\\n");
        this.inputString = inputString;
    }

    private void setCustomSeparator(String inputString) {
        int startIndex = 0;
        int lastIndex = 0;
        if (isDefaultSeparatorContained()) { //this.inputString 으로 판단하는게 좋은가?
            startIndex = inputString.indexOf("//");
            lastIndex = inputString.indexOf("\\n");
            this.customSeparator = inputString.substring(startIndex + 2, lastIndex); //커스텀 구분자는 그럼 한 개만인가?
        }
        // System.out.println(lastIndex);
        // System.out.println(this.inputString.charAt(2));
        // System.out.println(this.customSeparator);
    }

    private boolean isDefaultSeparatorContained() {
        return inputString.contains("//") && inputString.contains("\\n");
    }


    private void setFinalSeparator() {
        if (this.customSeparator.isEmpty()) {
            this.finalSeparator = "[" + "//" + "\n" + "]";
            return;
        }
        this.finalSeparator = "[" + "//" + "\n" + customSeparator + "]";
        System.out.println(this.finalSeparator);
    }

    private void setInputNumber(String inputString, String finalSeparator) {
        this.inputNumber = inputString.split(finalSeparator);
    }

    public String[] getInputNumber() {
        return this.inputNumber;
    }
}
