package com.hope.doctor.dao;

import java.util.List;

import com.hope.root.entity.SharingDetail;

public interface DoctorDAO {

	List<SharingDetail> getConsultations(String string, long userId);

	SharingDetail getConsultation(int consultationId);

	void updateSharingDetails(SharingDetail sd);

	SharingDetail getUserSharingDetail(int sharingId, long userId);

	SharingDetail getLastActive(int userId);

}
