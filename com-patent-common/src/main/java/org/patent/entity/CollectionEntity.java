package org.patent.entity;

public class CollectionEntity {
	private String collectible;//被收藏者
	private String collector;//收藏者
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCollectible() {
		return collectible;
	}
	public void setCollectible(String collectible) {
		this.collectible = collectible;
	}
	public String getCollector() {
		return collector;
	}
	public void setCollector(String collector) {
		this.collector = collector;
	}
	

}
