# 🔥 테오의 스프린트 15기 6조 Pick-Time Back-end

<br>

## 👨‍👩‍👦‍👦 조원

- [닝겐미키-Designer👩🏻‍💻]()
- [다모-Back-end](https://github.com/comody)
- [마크-Front-end](https://github.com/zwonkim)
- [마로-Front-end👩🏻‍💻](https://github.com/yooveloper)
- [엘라-Front-end](https://github.com/EllaSEON)
- [코마-Front-end](https://github.com/solar3070)
- [토마토-Back-end](https://github.com/devmtn30)
- [타몽-Front-end](https://github.com/blcklamb)

<br>

## 🏃🏽‍♀️🏃‍♂️ Kick-off

2023.06.21 20:30pm(KST)

<br />
<hr />

## Quick Start

1. `git clone https://github.com/pick-time/pick-time-fe.git`
2. ``
3. ``

<br />

## Stack

<br />

## Folder Structure

```

```

<br />

## Co-op Conventions

<br />

### Flow

모든 개발 및 협업과정은 Git Flow 를 따른다.

- 개발을 시작하기 전 issue를 생성한다.
  - issue는 본인이 작업할 내용을 적는다.
- develop 브랜치에서 분기하여 각자의 feature 브랜치를 생성하여 작업한다.
  - feature 브랜치는 `feature/기능이름` 형식으로 생성한다. `ex) feature/intro-page`
  - 띄어쓰기는 `-`로 작성 , 마침표 `.` 는 사용하지 않는다.
- 작업이 완료되면 develop 브랜치로 PR을 생성한다.
  - commit은 squash and merge로 한다.
  - PR은 Assignee로 본인을 지정한다.
  - PR 내용은 간단하게 작성하고 issue를 연결한다.
  - Approve를 1개 이상 받으면 develop 브랜치에 merge한다.

<br />

### Branch

- main: 현재 product 버전
- develop: 현재 개발 중인 feature가 모인 최신 버전
- feature: 단위 기능 개발 중인 브랜치
- hotfix: main 브랜치에서 발생한 버그를 수정하는 브랜치

<br />

### Commit Type

type은 다음 항목 중 하나를 선택해서 commit을 작성한다.

- build: 빌드 시스템이나 외부 종속성에 영향을 미치는 변경 사항
- ci: CI 구성 파일 및 스크립트에 대한 변경 사항
- docs: Documentation에 대한 변경 사항
- feat: 새로운 기능 추가
- fix: 버그 수정
- refactor: 버그 수정과 기능 추가 모두 포함되지 않는 기존 코드 변경 사항
- style: 스타일링 관련 사항
- test: 테스트 코드 추가 혹은 기존의 테스트 코드 수정에 대한 변경 사항
