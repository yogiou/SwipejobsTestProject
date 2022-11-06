package jie.wen.swipejobs.project.SwipejobsTestProject.services.impl;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllJobsService;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.GetAllWorkersService;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.JobMatchService;
import jie.wen.swipejobs.project.SwipejobsTestProject.utils.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JobMatchServiceImpl implements JobMatchService {
    private final GetAllJobsService getAllJobsService;
    private final GetAllWorkersService getAllWorkersService;

    @Value("#{${max.return.jobs}}")
    private int MAX_RETURN_JOBS;

    @Value("#{${distance.unit.value}}")
    public Map<String, Double> unitMap;

    public GetAllJobsService getGetAllJobsService() {
        return getAllJobsService;
    }

    public GetAllWorkersService getGetAllWorkersService() {
        return getAllWorkersService;
    }

    public JobMatchServiceImpl(@Autowired GetAllJobsService getAllJobsService, @Autowired GetAllWorkersService getAllWorkersService) {
        this.getAllJobsService = getAllJobsService;
        this.getAllWorkersService = getAllWorkersService;
    }
    @Override
    public List<JobDTO> matchJobs(int workerId) {
        List<JobDTO> jobDTOList = getAllJobsService.getAllJobs();
        List<WorkerDTO> workerDTOList = getAllWorkersService.getAllWorkers();

        WorkerDTO workerDTO = findWorker(workerId, workerDTOList);

        if (workerDTO == null) {
            return null;
        }

        return match(workerDTO, jobDTOList);
    }

    protected WorkerDTO findWorker(int workerId, List<WorkerDTO> workerDTOList) {
        return workerDTOList.stream().filter(item -> item.getUserId() == workerId).
                findAny().
                orElse(null);
    }

    protected List<JobDTO> match(WorkerDTO workerDTO, List<JobDTO> jobDTOList) {
        List<JobDTO> results = jobDTOList.stream().
                filter(jobDTO -> workerDTO.hasDriversLicense || !jobDTO.isDriverLicenseRequired()). // match all jobs if worker has driver license or no driver license required for the jobs if worker doesn't have
                filter(jobDTO -> jobDTO.getWorkersRequired() > 0). // jobs has vacancy
                filter(jobDTO -> { // worker has all the jobs required certificates
                    for (String cert : jobDTO.getRequiredCertificates()) {
                        if (!workerDTO.getCertificates().contains(cert)) {
                            return false;
                        }
                    }
                    return true;
                }).
                filter(jobDTO -> workerDTO.availability.size() > 0). // worker has availabilities
                filter(jobDTO -> isInJobDistance(workerDTO.jobSearchAddress.getLatitude(), workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getMaxJobDistance(), workerDTO.jobSearchAddress.getUnit(), jobDTO.getLocation().latitude, jobDTO.getLocation().longitude)). // match worker's job search acceptable work location
                sorted((j1, j2) -> { // sorted by highest bill rate, followed by work location
                    if (Double.compare(getPrice(j2.getBillRate()), (getPrice(j1.getBillRate()))) == 0) {
                        return Double.compare(DistanceUtil.getDistanceInMeters(workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getLatitude(), j1.getLocation().longitude, j1.getLocation().latitude), DistanceUtil.getDistanceInMeters(workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getLatitude(), j2.getLocation().longitude, j2.getLocation().latitude));
                    }

                    return Double.compare(getPrice(j2.getBillRate()), (getPrice(j1.getBillRate())));
                }).
                collect(Collectors.toList());

        return results.subList(0, Math.min(results.size(), MAX_RETURN_JOBS));
    }

    protected boolean isInJobDistance(float workerLat, float workerLon, float maxJobDistance, String unit, float jobLat, float jobLon) {
        return DistanceUtil.getDistanceInMeters(workerLon, workerLat, jobLon, jobLat) <= maxJobDistance *
                unitMap.getOrDefault(unit, 1d);
    }

    protected double getPrice(String price) {
        StringBuilder sb = new StringBuilder();

        for (char ch : price.toCharArray()) {
            if (ch >= '0' && ch <= '9' || ch == '.') {
                sb.append(ch);
            }
        }

        return Double.parseDouble(sb.toString());
     }
}
