package com.dana.admin.stockadmin.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component
public class RunningStatus {

    String  rsistatus;
    String  averagestatus;
    String  algostatus;
    String  importstatus;



}
