package calculator.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InputStringSeparator {
    private static final int CUSTOM_SEPARATOR_START_INDEX = 2;
    private String inputString;
    private String finalSeparator = ",|:";
    private String customSeparator = "";
    private String[] inputNumberString;
    private ArrayList<Integer> inputNumberInt = new ArrayList<>();
    private int startIndex;
    private int lastIndex;
    private boolean isCustomSeparatorContained = false;

    public InputStringSeparator(String inputString) {
        validateInputString(inputString);
        setInputString(inputString);
        if (isCustomSeparatorContained) {
            setCustomSeparator();
            addToFinalSeparator();
            extractInputNumber();
        }
        setInputNumber();
    }

    private void validateInputString(String inputString) {
        boolean isFirstCustomSeparator = inputString.contains("//");
        boolean isLastCustomSeparator = inputString.contains("\\n");
        if (!isFirstCustomSeparator && !isLastCustomSeparator) {
            return;
        }
        if (isFirstCustomSeparator != isLastCustomSeparator) {
            throw new IllegalArgumentException("형식을 지켜 커스텀 구분자를 지정해야 합니다.");
        }
        if (isFirstCustomSeparator && inputString.indexOf("//") != 0) {
            throw new IllegalArgumentException("커스텀 구분자를 먼저 지정한 후 숫자를 입력하세요.");
        }
        this.isCustomSeparatorContained = true;
    }

    private void setInputString(String inputString) {
        this.inputString = inputString.replace("\n", "\\n");
    }

    private void setCustomSeparator() {
        startIndex = inputString.indexOf("//") + CUSTOM_SEPARATOR_START_INDEX;
        lastIndex = inputString.indexOf("\\n");
        this.customSeparator = inputString.substring(startIndex, lastIndex);
    }

    private void addToFinalSeparator() {
        this.finalSeparator = ",|:|" + escapeSpecialChars(customSeparator);
    }

    private String escapeSpecialChars(String separator) {
        return separator.replace("\\", "\\\\")
                .replace(".", "\\.")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("?", "\\?")
                .replace("+", "\\+")
                .replace("*", "\\*")
                .replace("|", "\\|")
                .replace("^", "\\^")
                .replace("$", "\\$");
    }

    private void extractInputNumber() {
        this.inputString = inputString.substring(lastIndex + 2);
    }

    private void setInputNumber() {
        separateInputNumber();
        changeToInt();
    }

    private void separateInputNumber() {
        if (inputString.length() > 0) {
            this.inputNumberString = inputString.split(finalSeparator);
        }
    }

    private void changeToInt() {
        if (inputNumberString == null) {
            inputNumberInt.add(0);
            return;
        }

        Arrays.stream(inputNumberString).map(Integer::parseInt)
                .peek(parseInt -> {
                    if (parseInt < 0) {
                        throw new IllegalArgumentException("숫자는 양수를 입력해야 합니다.");
                    }
                })
                .forEach(inputNumberInt::add);
    }

    public List<Integer> getInputNumber() {
        return Collections.unmodifiableList(inputNumberInt);
    }
}
