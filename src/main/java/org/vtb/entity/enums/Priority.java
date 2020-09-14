package org.vtb.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    PLANNING("В планах"), VERY_LOW("Очень низкий"), LOW("Низкий"),
    MEDIUM("Средний"), HIGH("Высокий"), VERY_HIGH("Очень высокий");

    private String rus;
}
