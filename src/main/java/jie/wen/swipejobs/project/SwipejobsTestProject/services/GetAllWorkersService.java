package jie.wen.swipejobs.project.SwipejobsTestProject.services;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;

import java.util.List;

public interface GetAllWorkersService {
    List<WorkerDTO> getAllWorkers();
    List<WorkerDTO> getAllWorkers(String uri);
}
