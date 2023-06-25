package com.teo15.picktimebe.target.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetTargetUserName {
    private String providerName;
    private String consumerName;
    public GetTargetUserName(String providerName, String consumerName){
        this.consumerName = consumerName;
        this.providerName = providerName;
    }

}
