package jie.wen.swipejobs.project.SwipejobsTestProject.services.impl;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GetAllJobsServiceImpl implements GetAllJobsService {
    private final RestTemplate restTemplate;

    @Value("#{${base.api}}")
    private String HOST;

    @Value("#{${job.end.point}}")
    private String JOB_END_POINT;

    public GetAllJobsServiceImpl(@Autowired RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return getAllJobs(HOST + JOB_END_POINT);
    }

    /**
     * @param uri
     * @return
     */
    @Override
    public List<JobDTO> getAllJobs(String uri) {
        ResponseEntity<JobDTO[]> responseEntity = restTemplate.getForEntity(uri, JobDTO[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}
