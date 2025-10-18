package top.yanquithor.framework.dddbase.common.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DomainModel {
    
    private long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String status;
}
