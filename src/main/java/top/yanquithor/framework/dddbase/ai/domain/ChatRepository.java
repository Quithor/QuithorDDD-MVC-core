package top.yanquithor.framework.dddbase.ai.domain;

import top.yanquithor.framework.dddbase.common.domain.model.DomainModel;
import top.yanquithor.framework.dddbase.common.domain.repository.BaseRepository;

public interface ChatRepository<T extends DomainModel> extends BaseRepository<T> {
}
