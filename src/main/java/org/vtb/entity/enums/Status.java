package org.vtb.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    CREATED("Создана"), INPROGRESS("В работе"), CHECKING("Передана на проверку"),
    RETURNED("Возвращена на доработку"), COMPLETED("Завершена"), CANCELED("Отменена");

    private String rus;
}
