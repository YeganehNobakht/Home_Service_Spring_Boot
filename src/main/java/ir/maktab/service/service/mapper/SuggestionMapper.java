package ir.maktab.service.service.mapper;

import ir.maktab.service.dto.SuggestionDto;
import org.springframework.stereotype.Component;

@Component
public class SuggestionMapper {

    private final Mapper mapper;

    public SuggestionMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    ir.maktab.service.data.entity.Suggestion toSuggestion(SuggestionDto suggestionDto) {
        return new ir.maktab.service.data.entity.Suggestion()
                .setSuggestionStatus(suggestionDto.getSuggestionStatus())
                .setCustomerOrder(mapper.toCustomerOrder(suggestionDto.getCustomerOrder()))
                .setDurationOfWork(suggestionDto.getDurationOfWork())
                .setId(suggestionDto.getId())
                .setPrice(suggestionDto.getPrice())
                .setSpecialist(mapper.toSpecialist(suggestionDto.getSpecialistDto()))
                .setStartTime(suggestionDto.getStartTime())
                .setWorkDescription(suggestionDto.getWorkDescription());
    }

    SuggestionDto toSuggestionDto(ir.maktab.service.data.entity.Suggestion suggestion) {
        return new SuggestionDto()
                .setSuggestionStatus(suggestion.getSuggestionStatus())
                .setCustomerOrder(mapper.toCustomerOrderDto(suggestion.getCustomerOrder()))
                .setDurationOfWork(suggestion.getDurationOfWork())
                .setId(suggestion.getId())
                .setPrice(suggestion.getPrice())
                .setSpecialistDto(mapper.toSpecialistDto(suggestion.getSpecialist()))
                .setStartTime(suggestion.getStartTime())
                .setWorkDescription(suggestion.getWorkDescription());
    }
}
