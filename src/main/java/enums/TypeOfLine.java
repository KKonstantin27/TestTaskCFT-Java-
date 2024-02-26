package enums;

import lombok.Getter;

@Getter
public enum TypeOfLine {
    FLOAT(0), INTEGER(1), STRING(2);
    final int pathIndex;

    TypeOfLine(int pathIndex) {
        this.pathIndex = pathIndex;
    }
}
