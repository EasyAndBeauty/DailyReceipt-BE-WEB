insert into token(unique_id_by_social, refresh_token)
values ('unique1', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyZWNlaXB0Iiwic3ViIjoidW5pcXVlMSIsImF1ZCI6IlJFRlJFU0giLCJleHAiOjE2OTM5MjE1ODcsImlhdCI6MTY2MjM4NTU4NywiYXV0aCI6IlVTRVIifQ.qy27pR38E2zgHTLxxXy-zwfp4HpAtWFTbvB_0QhJxpEDbb-eBV1uNHXqalYeizCl6MLAFiJS2tlmxZVkJrOAyw');

insert into token(unique_id_by_social, refresh_token)
values ('unique2', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyZWNlaXB0Iiwic3ViIjoidW5pcXVlMiIsImF1ZCI6IlJFRlJFU0giLCJleHAiOjE2OTM5MjE2NjEsImlhdCI6MTY2MjM4NTY2MSwiYXV0aCI6IlVTRVIifQ.kj2CPZkLxWn-L2vDIhMm-_FxzojWBpqjY2MenTQwZBwmZTKe3-iB-hIYeYqT7PxrpBjlrFZn3f0g2J4AYoPdKg');

insert into token(unique_id_by_social, refresh_token)
values ('unique3', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyZWNlaXB0Iiwic3ViIjoidW5pcXVlMyIsImF1ZCI6IlJFRlJFU0giLCJleHAiOjE2OTM5MjE2NzcsImlhdCI6MTY2MjM4NTY3NywiYXV0aCI6IlVTRVIifQ.1zWTY1T3ee7f5kAYoZ8j-3cmCnMwuCIpjjgAV61ERrPG2dyVKfNSZPCrVFnXSy0_BpbRrirI5Be9qt90xDDfeQ');

insert into token(unique_id_by_social, refresh_token)
values ('unique4', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJyZWNlaXB0Iiwic3ViIjoidW5pcXVlNCIsImF1ZCI6IlJFRlJFU0giLCJleHAiOjE2OTM5MjM3NzIsImlhdCI6MTY2MjM4Nzc3MiwiYXV0aCI6IlVTRVIifQ.VhCCHQo3RxDc2x3ya0xb6l-sHWuvIX93dhR_sbpld0aOVUVcgroxPVldG4_7M-R_A8CyFgJNeiz07own10mxXQ');

insert into account (account_id, token_id, nickname) values (1, 1, 'user1');
insert into account (account_id, token_id, nickname) values (2, 2, 'user2');
insert into account (account_id, token_id, nickname) values (3, 3, 'user3');
insert into account (account_id, token_id, nickname) values (4, 4, 'user4');

insert into todo (todo_id, created_at, updated_at, is_done, task, timer, account_id, date)
values (1, '2021-08-27 09:00:00', '2021-08-27 09:00:00', 1, 'TDD 공부', '250', 1, '2022-04-14');

insert into todo (todo_id, created_at, updated_at, is_done, task, timer, account_id, date)
values (2, '2021-08-27 09:00:00', '2021-08-27 09:00:00', 1, 'ATDD 공부', '270', 1, '2022-04-15');