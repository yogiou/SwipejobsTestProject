package jie.wen.swipejobs.project.SwipejobsTestProject.services.impl;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllWorkersService;
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
public class GetAllWorkersServiceImpl implements GetAllWorkersService {
    private final RestTemplate restTemplate;

    @Value("#{${base.api}}")
    private String HOST;

    @Value("#{${worker.end.point}}")
    private String WORKER_END_POINT;

    public GetAllWorkersServiceImpl(@Autowired RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public List<WorkerDTO> getAllWorkers() {
        return getAllWorkers(HOST + WORKER_END_POINT);
    }

    @Override
    public List<WorkerDTO> getAllWorkers(String uri) {
        ResponseEntity<WorkerDTO[]> responseEntity = restTemplate.getForEntity(uri, WorkerDTO[].class);
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }
}
