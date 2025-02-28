package com.webapp.websiteportal.dto;

import com.webapp.websiteportal.entity.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageModel {
    private Long key;
    private String languageName;
    private Boolean canRead;
    private Boolean canWrite;
    private Boolean canSpeak;

    public LanguageModel(Language language) {
        this.key = language.getKey();
        this.languageName = language.getLanguageName().name();
        this.canRead = language.getCanRead();
        this.canWrite = language.getCanWrite();
        this.canSpeak = language.getCanSpeak();
    }
}
