select ename
from emp
where mgr is null;

#조인 사용
select e.ename , 
		d.dname
        from emp as e join dept as d on e.deptno =d.deptno;
        
        
#스칼라 부속질
select ename,(select dname from dept where deptno =e.deptno) as dname
from emp as e;


#3-1조인
select e.ename
from emp as e 
join dept as d on e.deptno =d.deptno
where d.loc='chicago';



#인라인 뷰
select ename
from emp as e
where deptno in(select deptno from dept where loc='chicago');


#exists
select e.ename
from emp as e
where exists (select * 
from dept as d 
where d.deptno= e.deptno 
and d.loc='chicago');


#평균보다 높은 급여의 직원

select ename
from emp
where sal>(select avg(sal) from emp);


#자기부서 평균보다 급여가 많은 사람

select e.ename
from emp as e
where e.sal>(select avg(sal) from emp where deptno=e.deptno);
