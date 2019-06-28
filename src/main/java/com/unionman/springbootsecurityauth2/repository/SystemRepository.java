package com.unionman.springbootsecurityauth2.repository;
import com.unionman.springbootsecurityauth2.entity.SystemData;
import com.unionman.springbootsecurityauth2.repository.base.BaseRepository;

public interface SystemRepository extends BaseRepository<SystemData> {
    SystemData findSystemByCallTime(int callTime);
}
