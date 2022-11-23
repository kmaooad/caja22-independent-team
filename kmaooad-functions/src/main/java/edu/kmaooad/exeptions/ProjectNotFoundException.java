package edu.kmaooad.exeptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String projectId) {
        super(String.format("Project with id %s not found", projectId));
    }
}
