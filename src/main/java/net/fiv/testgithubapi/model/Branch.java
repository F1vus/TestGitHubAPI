package net.fiv.testgithubapi.model;

import lombok.Getter;

@Getter
public class Branch {
    private String name;
    private Commit commit;
}
