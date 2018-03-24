package cn.ouctechnology.dao;

import cn.ouctechnology.domain.Upload;

import java.util.List;

public interface IUploadDao {
    void save(Upload upload);

    List<Upload> getByToken(Long token);
}
