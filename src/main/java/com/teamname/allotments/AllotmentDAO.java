package com.teamname.allotments;

import java.util.List;
import java.util.Optional;

public interface AllotmentDAO {
    List<Allotment> selectAllAllotments();
    Optional<Allotment> selectAllotmentById();
    int createAllotment();
}
