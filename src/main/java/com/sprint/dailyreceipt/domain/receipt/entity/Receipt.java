package com.sprint.dailyreceipt.domain.receipt.entity;

import com.sprint.dailyreceipt.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Receipt extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> todoIds = new ArrayList<>();

    private boolean pinned;

    private String famousSaying;

    private String name;

    public Receipt(Long id, List<Integer> todoIds, boolean pinned, String famousSaying, String name) {
        this.id = id;
        this.todoIds = todoIds;
        this.pinned = pinned;
        this.famousSaying = famousSaying;
        this.name = name;
    }

    @Builder
    public Receipt(ZonedDateTime createdAt, ZonedDateTime updatedAt, Long id, List<Integer> todoIds,
                   boolean pinned, String famousSaying, String name) {
        super(createdAt, updatedAt);
        this.id = id;
        this.todoIds = todoIds;
        this.pinned = pinned;
        this.famousSaying = famousSaying;
        this.name = name;
    }
}
