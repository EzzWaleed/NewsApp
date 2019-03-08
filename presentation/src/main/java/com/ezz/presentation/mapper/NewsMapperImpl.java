package com.ezz.presentation.mapper;

import com.ezz.domain.entity.NewsDomain;
import com.ezz.domain.resource.Resource;
import com.ezz.presentation.model.NewsUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;


/**
 * Created by Ezz Waleed on 07,March,2019
 */

/**
 * Implementation of {@link NewsMapper}
 */
public class NewsMapperImpl implements NewsMapper {

	@Inject
	public NewsMapperImpl() {
	}

	@Override
	public NewsUI mapToUI(@NonNull NewsDomain newsDomain) {
		NewsUI newsUI = new NewsUI();
		newsUI.setContent(newsDomain.getContent());
		newsUI.setDescription(newsDomain.getDescription());
		newsUI.setImageUrl(newsDomain.getImageUrl());
		newsUI.setPublishedDate(convertToDate(newsDomain.getPublishedDate()));
		newsUI.setSourceName(newsDomain.getSourceName());
		newsUI.setTitle(newsDomain.getTitle());
		newsUI.setUrl(newsDomain.getUrl());
		return newsUI;
	}

	/**
	 * convert time in milliseconds to Date
	 * @param time required time in milliseconds
	 * @return resulted date
	 */
	private Date convertToDate(@NonNull Long time){
		return new Date(time);
	}

	@Override
	public List<NewsUI> mapToUIList(@NonNull List<NewsDomain> newsDomainList) {
		List<NewsUI> newsUIList = new ArrayList<>();
		for (NewsDomain newsDomain : newsDomainList){
			newsUIList.add(mapToUI(newsDomain));
		}
		return newsUIList;
	}

	@Override
	public Resource<List<NewsUI>> mapToUIResourceList(@NonNull Resource<List<NewsDomain>> domainResource) {
		if (domainResource.data != null)
			return new Resource<>(domainResource.status, mapToUIList(domainResource.data), domainResource.message);
		else
			return new Resource<>(domainResource.status, null, domainResource.message);
	}
}
