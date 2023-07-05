package com.teo15.picktimebe.target.dto;

import com.teo15.picktimebe.target.Target;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateTargetRequest {
    private String providerName;
    private String consumerName;

    public CreateTargetRequest(String providerName, String consumerName) {
        this.providerName = providerName;
        this.consumerName = consumerName;
    }

    public Target toEntity(){
        return new Target(providerName, consumerName);
    }
}
