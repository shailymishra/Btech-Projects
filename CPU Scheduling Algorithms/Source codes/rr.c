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




void sigusr_handler(int signo)
{
  if (SIGUSR1 == signo)
    {
      pause();
    }
}


void * method ( void * param ){
	char *arr = ( char * ) param;


pause();
FILE *fp;
int count=0;
char c;
fp=fopen(arr,"r");
int i=0;
for (c = getc(fp); c != EOF; c = getc(fp))
        if (c == '\n')
            count = count + 1;
fseek(fp,0,SEEK_SET);

int arrvial_time = ptime;
int thistime = 0;

for ( i=0 ; i<count ; i++)
{
	ex='\0';
	printf("%s \t Result: %d\n", arr ,expr(fp));
	fseek(fp,1,SEEK_CUR);
	ptime = ptime + 1;
	//printf(" ptime is : %d " , ptime);
	thistime = thistime+1;	
	//printf("   \n %s \t i is %d" , arr, i);
	if( thistime == count )
	thistime = ptime;
		
	sleep(1);
	
}
fclose(fp);
//printf( " %s \t Arrival Time : %d \t Finish Time  : %d \n  \t " , arr , arrvial_time , thistime );
f = fopen("Log_RR","a");
fprintf(f , " %s  Arrival Time : %d  Finish Time : %d  Time Taken : %d \n " , arr , arrvial_time , thistime , (thistime-arrvial_time));
fclose(f);

pthread_exit(NULL);
}

int
main(int argc, char ** argv)
{
  struct sigaction signal_action;
  memset(&signal_action, 0, sizeof(signal_action));
  signal_action.sa_handler = sigusr_handler;
  sigemptyset(&signal_action.sa_mask);

  sigaction(SIGUSR1, &signal_action, NULL);
  sigaction(SIGUSR2, &signal_action, NULL);

    {
      pthread_t threads[THREADSMAX] =
        { 0 };

      intptr_t iThread = 0;

	char a[]="process1";
	char b[]="process2";
	char c[]="process3";

int iResult;
          iResult = pthread_create(&threads[0], NULL, method,    (void *) a);
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }
         iResult = pthread_create(&threads[1], NULL, method,    (void *) b);
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }
         iResult = pthread_create(&threads[2], NULL, method,    (void *) c);
          if (iResult)
            {
              errno = iResult;
              perror("pthread_created()");
              exit(1);
            }
         

      sleep(1); /* Unreliable workaround: Try to make sure all threads have started and block in "pause()". See comments on how this might be fixed nicely ... */

      /* scheduling loop */
      for (iThread = 0; ; ++iThread)
        {
          if (THREADSMAX == iThread)
            {
              iThread = 0;
            }
            /* Resume current thread */
           {
             iResult = pthread_kill(threads[iThread], SIGUSR2);
             if (iResult)
                {
                  errno = iResult;
              //   perror("pthread_kill(..., SIGUSR2)");
               //  exit(2);
             }
            }

          sleep(SCHEDULING_INTERVALL);

          /* Suspend current thread */
           {
             iResult = pthread_kill(threads[iThread], SIGUSR1);
           if (iResult)
               {
               errno = iResult;
	//printf(" \n Time : %d \n " , ptime);
           //    perror("pthread_kill(..., SIGUSR1)");
            //      exit(3);
              }
          }
       }
    }


//exit(1);

  return 0;
}
