package jie.wen.swipejobs.project.SwipejobsTestProject.controller;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllJobsService;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllWorkersService;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.JobMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobmatch")
public class GetJobsForWorkerController {
    private final JobMatchService jobMatchService;
    private final GetAllJobsService getAllJobsService;
    private final GetAllWorkersService getAllWorkersService;

    @Value("#{${max.return.jobs}}")
    private int MAX_RETURN_JOBS;

    @Value("#{${distance.unit.value}}")
    public Map<String, Double> unitMap;

    public GetJobsForWorkerController(@Autowired JobMatchService jobMatchService, @Autowired GetAllJobsService getAllJobsService, @Autowired GetAllWorkersService getAllWorkersService) {
        this.jobMatchService = jobMatchService;
        this.getAllJobsService = getAllJobsService;
        this.getAllWorkersService = getAllWorkersService;
    }

    @GetMapping(value = "/{workerId}", produces = "application/json")
    public List<JobDTO> getJobs(@PathVariable Integer workerId) throws Exception {
        if (workerId == null) {
            throw new Exception("No worker id found error");
        }

        List<JobDTO> jobDTOList = getAllJobsService.getAllJobs();
        List<WorkerDTO> workerDTOList = getAllWorkersService.getAllWorkers();

        return jobMatchService.matchJobs(workerId, MAX_RETURN_JOBS, unitMap, jobDTOList, workerDTOList);
    }
}
