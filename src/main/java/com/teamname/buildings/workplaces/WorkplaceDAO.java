package com.teamname.buildings.workplaces;

import java.util.List;
import java.util.Optional;


public interface WorkplaceDAO {
    List<Workplace> selectAllWorkplace();
    Optional<Workplace> selectWorkplaceById(int id);
    int createWorkplace (Workplace workplace);
    int deleteWorkplace (int id);
    int updateWorkplace (int id, Workplace workplace);

}
