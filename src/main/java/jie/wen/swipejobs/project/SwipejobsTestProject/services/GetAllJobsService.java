package jie.wen.swipejobs.project.SwipejobsTestProject.services;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;

import java.util.List;

public interface GetAllJobsService {
    List<JobDTO> getAllJobs();
    List<JobDTO> getAllJobs(String uri);
}
