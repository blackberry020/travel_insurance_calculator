package lv.javaguru.travel.insurance.core.validations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.javaguru.travel.insurance.dto.TravelCalculatePremiumRequest;
import lv.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
@Getter
@RequiredArgsConstructor
class TravelRequestDateFromNotPastValidation extends TravelRequestValidationImpl {

    private final long allowedDelayFromPresent = 5000L;

    private final long millisecondsNow =  LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> check(TravelCalculatePremiumRequest request) {
        if (request.getAgreementDateFrom() == null) return Optional.empty();

        return (request.getAgreementDateFrom().getTime() + allowedDelayFromPresent < millisecondsNow)
                ? Optional.of(errorFactory.buildError("ERROR_CODE_1"))
                : Optional.empty();
    }
}
