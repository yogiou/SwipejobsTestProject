package jie.wen.swipejobs.project.SwipejobsTestProject.services;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;

import java.util.List;
import java.util.Map;

public interface JobMatchService {
    List<JobDTO> matchJobs(int workerId, int max_return_jobs, Map<String, Double> unitMap, List<JobDTO> jobDTOList, List<WorkerDTO> workerDTOList);
}
