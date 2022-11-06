package jie.wen.swipejobs.project.SwipejobsTestProject.services;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;

import java.util.List;

public interface JobMatchService {
    List<JobDTO> matchJobs(int workerId);
}
