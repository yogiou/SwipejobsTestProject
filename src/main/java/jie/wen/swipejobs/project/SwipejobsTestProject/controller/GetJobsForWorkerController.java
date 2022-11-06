package jie.wen.swipejobs.project.SwipejobsTestProject.controller;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.JobMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobmatch")
public class GetJobsForWorkerController {
    private final JobMatchService jobMatchService;

    public GetJobsForWorkerController(@Autowired JobMatchService jobMatchService) {
        this.jobMatchService = jobMatchService;
    }

    @GetMapping(value = "/{workerId}", produces = "application/json")
    public List<JobDTO> getJobs(@PathVariable Integer workerId) throws Exception {
        if (workerId == null) {
            throw new Exception("No worker id found error");
        }

        return jobMatchService.matchJobs(workerId);
    }
}
