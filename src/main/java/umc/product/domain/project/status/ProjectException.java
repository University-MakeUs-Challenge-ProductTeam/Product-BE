package umc.product.domain.project.status;

import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.BaseCodeInterface;

public class ProjectException extends RestApiException {

    public ProjectException(BaseCodeInterface errorCode) {
        super(errorCode);
    }
}
