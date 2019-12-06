package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.utils.TagHelper;

import java.util.List;

@Component
public class TagValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            String tags = (String) target;
            List<String> tagsArray = TagHelper.parseTags(tags);
            for (String tag : tagsArray) {
                if (!tag.matches("[a-zA-Z]+")) {
                    errors.reject("tags.invalid-tags", "invalid tags");
                }
            }
        }
    }
}
