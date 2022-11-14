package jie.wen.swipejobs.project.SwipejobsTestProject.services.impl;

import jie.wen.swipejobs.project.SwipejobsTestProject.data.JobDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.data.WorkerDTO;
import jie.wen.swipejobs.project.SwipejobsTestProject.services.JobMatchService;
import jie.wen.swipejobs.project.SwipejobsTestProject.utils.DistanceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class JobMatchServiceImpl implements JobMatchService {
    /**
     * @param workerId
     * @param max_return_jobs
     * @param unitMap
     * @param jobDTOList
     * @param workerDTOList
     * @return
     */
    @Override
    public List<JobDTO> matchJobs(int workerId, int max_return_jobs, Map<String, Double> unitMap, List<JobDTO> jobDTOList, List<WorkerDTO> workerDTOList) {
        WorkerDTO workerDTO = findWorker(workerId, workerDTOList);

        if (workerDTO == null) {
            return null;
        }

        return match(workerDTO, jobDTOList, max_return_jobs, unitMap);
    }

    /**
     * @param workerId
     * @param workerDTOList
     * @return
     */
    protected WorkerDTO findWorker(int workerId, List<WorkerDTO> workerDTOList) {
        return workerDTOList.stream().filter(item -> item.getUserId() == workerId).
                findAny().
                orElse(null);
    }

    /**
     * @param workerDTO
     * @param jobDTOList
     * @param max_return_jobs
     * @param unitMap
     * @return
     */
    protected List<JobDTO> match(WorkerDTO workerDTO, List<JobDTO> jobDTOList, int max_return_jobs, Map<String, Double> unitMap) {
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
                filter(jobDTO -> isInJobDistance(workerDTO.jobSearchAddress.getLatitude(), workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getMaxJobDistance(), workerDTO.jobSearchAddress.getUnit(), jobDTO.getLocation().latitude, jobDTO.getLocation().longitude, unitMap)). // match worker's job search acceptable work location
                sorted((j1, j2) -> { // sorted by highest bill rate, followed by work location
                    if (Double.compare(getPrice(j2.getBillRate()), (getPrice(j1.getBillRate()))) == 0) {
                        return Double.compare(DistanceUtil.getDistanceInMeters(workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getLatitude(), j1.getLocation().longitude, j1.getLocation().latitude), DistanceUtil.getDistanceInMeters(workerDTO.jobSearchAddress.getLongitude(), workerDTO.jobSearchAddress.getLatitude(), j2.getLocation().longitude, j2.getLocation().latitude));
                    }

                    return Double.compare(getPrice(j2.getBillRate()), (getPrice(j1.getBillRate())));
                }).
                collect(Collectors.toList());

        return results.subList(0, Math.min(results.size(), max_return_jobs));
    }

    /**
     * @param workerLat
     * @param workerLon
     * @param maxJobDistance
     * @param unit
     * @param jobLat
     * @param jobLon
     * @param unitMap
     * @return
     */
    protected boolean isInJobDistance(float workerLat, float workerLon, float maxJobDistance, String unit, float jobLat, float jobLon, Map<String, Double> unitMap) {
        return DistanceUtil.getDistanceInMeters(workerLon, workerLat, jobLon, jobLat) <= maxJobDistance *
                unitMap.getOrDefault(unit, 1d);
    }

    /**
     * @param price
     * @return
     */
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
