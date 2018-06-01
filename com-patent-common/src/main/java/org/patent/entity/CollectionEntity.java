package org.patent.entity;

public class CollectionEntity {
	private String collectibleAcount;//被收藏者
	private String collectorAcount;//收藏者
	private Long id;
//	private String collector;
//	private String collectible;
//	public String getCollectible() {
//		return collectible;
//	}
//	public void setCollectible(String collectible) {
//		this.collectible = collectible;
//	}
//	public String getCollector() {
//		return collector;
//	}
//	public void setCollector(String collector) {
//		this.collector = collector;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCollectibleAcount() {
		return collectibleAcount;
	}
	public void setCollectibleAcount(String collectibleAcount) {
		this.collectibleAcount = collectibleAcount;
	}
	public String getCollectorAcount() {
		return collectorAcount;
	}
	public void setCollectorAcount(String collectorAcount) {
		this.collectorAcount = collectorAcount;
	}
	

}
