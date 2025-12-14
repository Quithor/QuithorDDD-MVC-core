package top.yanquithor.framework.dddbase.common.infrastructure.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Base Data Object
 *
 * @author YanQuithor
 * @version 1.1.1
 * @since 2025-12-13
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseDO {

    @TableId
    private long id;

    @TableField
    private LocalDateTime createTime;

    @TableField
    private LocalDateTime updateTime;

    @TableField
    private String status;
}
