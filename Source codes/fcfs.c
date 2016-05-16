#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <signal.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include<stdio.h>
#include<ctype.h>
#include<string.h>


#define THREADSMAX (3)
#define SCHEDULING_INTERVALL (1) /* seconds */

char ex='\0';
int ptime=0;
  FILE *f;



pthread_mutex_t var; 

void err(const char* s)
{
perror(s);
exit(0);
}


int factor(FILE *fp )
{
	int val,i;
	char ch;
	ch = fgetc(fp);	
	switch(ch)
	{
		case '(':
		val=expr(fp);
		scanf("%c",&ch);
		if(ch!=')')
		err("Missing closing paranthesis in factor.");
		break;
		default :{
				if((ch>'0')&&(ch<='9'))
				val=atoi(&ch);
				else
				err("Illegal character sequence in place of factor.");
			}
	}
	return val;
}


int term(FILE *fp )
{
	int val,k;
	char ch[10];
	val=factor(fp);
	while(1)
	{
		if( ex == '\0')	
		ch[0] = fgetc(fp);	
		else
		{
		ch[0] = ex;
		ex = '\0';
		}		

		if(ch[0]=='*')
		{
			val=val*factor(fp);
		}
		else
	break;
	}
	ex = ch[0];
	return val;
}


int expr (FILE *fp)
{
	int val,k;
	char ch[10];
	val=term(fp);
	while(1)
	{
		if( ex == '\0')	
		ch[0] = fgetc(fp);	
		else
		{
		ch[0] = ex;
		ex = '\0';
		}	
		if(ch[0]=='+')
		val=val+term(fp);
		else
		break;
	}
	ex = ch[0];
	return val;
}




void * method ( void * param )
{
	char *arr = ( char * ) param;


int arrvial_time = ptime;


pthread_mutex_lock(&var); 

FILE *fp;
int count=0;
char c;
fp=fopen(arr,"r");
int i=0;

for (c = getc(fp); c != EOF; c = getc(fp))
        if (c == '\n')
            count = count + 1;
fseek(fp,0,SEEK_SET);

int thistime = 0;

for ( i=0 ; i<count ; i++)
{
	ex='\0';
	printf("%s \t Result: %d\n", arr ,expr(fp));
	fseek(fp,1,SEEK_CUR);
	ptime = ptime + 1;
	thistime = thistime+1;	
	if( thistime == count )
	thistime = ptime;

	sleep(1);	
}

fclose(fp);
f = fopen("Log_FSFC","a");
fprintf(f , " %s   Arrival Time : %d      Finish Time : %d    Time Taken : %d \n " , arr , arrvial_time , thistime , (-arrvial_time + thistime) );
fclose(f);

pthread_mutex_unlock(&var); 


pthread_exit(NULL);

}

int
main(int argc, char ** argv)
{



      pthread_t threads[THREADSMAX] = { 0 };


    pthread_mutex_init(&var,NULL);

	char a[]="process3";
	char b[]="process2";
	char c[]="process1";

int iResult;
          iResult = pthread_create(&threads[0], NULL, method,    (void *) a);
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }


	
	iResult = pthread_create(&threads[1], NULL, method,    (void *) b );
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }

	
	
	iResult = pthread_create(&threads[2], NULL, method,    (void *) c );
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }

pthread_join(threads[0], NULL);


	pthread_join(threads[1], NULL);

	pthread_join(threads[2], NULL); 



//exit(1);

  return 0;
}
